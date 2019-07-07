package ru.virtu.systems.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.virtu.systems.dto.base.BaseEnity;
import ru.virtu.systems.query.operator.*;
import ru.virtu.systems.sc.base.OrderBy;
import ru.virtu.systems.util.Codable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @author Alexey Pustohin
 */
public class SqlQuery implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlQuery.class);
    
    private String query;
    private List<SqlOperator> whereOperators = new ArrayList<>();
    private List<SqlOperator> havingOperators = new ArrayList<>();
    
    private Map<String, SqlQuery> cteQueries = new LinkedHashMap<>();
    private Map<String, SqlQuery> nestedQueries = new LinkedHashMap<>();

    private List<String> grouping = new ArrayList<>();
    private List<OrderBy> ordering = new ArrayList<>();
    
    private Long offset;
    private Long limit;

    public SqlQuery(String query) {
        this.query = query;
    }
    
    public String getSql() {
        StringBuilder res = new StringBuilder();
        
        // Добавляем предложение with
        if (!cteQueries.isEmpty()) {
            res.append("with\n");
            
            String prefix = "";
            for (Map.Entry<String, SqlQuery> cte : cteQueries.entrySet()) {
                res.append(prefix).append(cte.getKey()).append(" as (\n")
                        .append(cte.getValue().getSql())
                        .append("\n)\n");
                prefix = ", ";
            }
        }

        // Добавляем сам запрос select
        res.append(query);

        // Добавляем вложенные запросы в предложение from
        if (!nestedQueries.isEmpty()) {
            String prefix;
            if (!query.contains("from")) { // TODO Такая себе проверка
                prefix = " from (";
            } else {
                prefix = ", (";
            }

            for (Map.Entry<String, SqlQuery> nested : nestedQueries.entrySet()) {
                res.append(prefix).append(nested.getValue().getSql()).append(") ").append(nested.getKey());
                prefix = ", (";
            }
        }

        // Добавляем предложения where и having
        res.append(buildConjuctivePart("where", whereOperators));
        res.append(buildConjuctivePart("having", havingOperators));

        // Добавляем предложение group by
        if (!grouping.isEmpty()) {
            res.append(" group by ");
            String prefix = "";
            for (String groupBy : grouping) {
                res.append(prefix).append(groupBy);
                prefix = ", ";
            }
        }

        // Добавляем предложение order by
        if (!ordering.isEmpty()) {
            res.append(" order by ");
            String prefix = "";
            for (OrderBy orderBy : ordering) {
                res.append(prefix).append(orderBy.getProperty())
                        .append(" ")
                        .append(orderBy.getOrdering() == OrderBy.Ordering.DESC ? " desc" : " asc");
                prefix = ", ";
            }
        }

        if (offset != null) {
            res.append(" offset ?");
        }

        if (limit != null) {
            res.append(" limit ?");
        }

        String resStr = res.toString();
        logger.debug("Query has been converted into: " + resStr);
        
        return resStr;
    }

    public Object[] getArguments() {
        List<Object> arguments = new ArrayList<>();
        
        cteQueries.forEach((a, q) -> arguments.addAll(Arrays.asList(q.getArguments())));
        nestedQueries.forEach((a, q) -> arguments.addAll(Arrays.asList(q.getArguments())));
        
        filterByInclude(this.whereOperators).forEach(op -> arguments.addAll(op.getArguments()));
        filterByInclude(this.havingOperators).forEach(op -> arguments.addAll(op.getArguments()));

        if (offset != null) {
            arguments.add(offset);
        }

        if (limit != null) {
            arguments.add(limit);
        }
        
        return arguments.stream().map(this::modifyArgument).toArray();
    }

    private Object modifyArgument(Object argument) {
        // TODO Не снижает ли эта фигня производиельность?
        if (argument instanceof Codable) {
            return ((Codable) argument).getCode();
        }

        if (argument instanceof BaseEnity) {
            return ((BaseEnity) argument).getId();
        }

        return argument;
    }

    private String buildConjuctivePart(String expression, List<SqlOperator> operators) {
        StringBuilder expressionBuilder = new StringBuilder();
        AtomicReference<String> prefix = new AtomicReference<>("");
        filterByInclude(operators).forEachOrdered(op -> {
            expressionBuilder.append(prefix.get()).append(op.getSql());
            prefix.set(" and ");
        });

        String res = expressionBuilder.toString();
        if (!res.isEmpty()) {
            res = " " + expression + " " + res;
        }

        return res;
    }
    
    private Stream<SqlOperator> filterByInclude(List<SqlOperator> operators) {
        return operators.stream().filter(SqlOperator::includesToQuery);
    }

    /*
        Хелперы для группировки
     */

    public SqlQuery groupBy(String... groupBy) {
        grouping.addAll(Arrays.asList(groupBy));
        return this;
    }

    /*
        Хелперы для сортировки
     */

    public SqlQuery orderBy(String property) {
        ordering.add(new OrderBy(property));
        return this;
    }

    public SqlQuery orderBy(String property, OrderBy.Ordering ordering) {
        this.ordering.add(new OrderBy(property, ordering));
        return this;
    }

    public SqlQuery orderBy(List<OrderBy> orderByList) {
        ordering.addAll(orderByList);
        return this;
    }

    /*
        Хелперы для offset и limit
     */

    public SqlQuery limit(Long limit) {
        this.limit = limit;
        return this;
    }

    public SqlQuery offset(Long offset) {
        this.offset = offset;
        return this;
    }

    /*
        Хелперы для where
     */
    
    public SqlQuery eq(String columnName, Object argument) {
        return whereBinary("=", columnName, argument);
    }

    public SqlQuery neq(String columnName, Object argument) {
        return whereBinary("<>", columnName, argument);
    }

    public SqlQuery ge(String columnName, Object argument) {
        return whereBinary(">=", columnName, argument);
    }

    public SqlQuery g(String columnName, Object argument) {
        return whereBinary(">", columnName, argument);
    }

    public SqlQuery le(String columnName, Object argument) {
        return whereBinary("<=", columnName, argument);
    }

    public SqlQuery l(String columnName, Object argument) {
        return whereBinary("<", columnName, argument);
    }
    
    public SqlQuery like(String columnName, String argument) {
        return where(new LikeOperator(columnName, argument));
    }
    
    public SqlQuery like(String columnName, String argument, LikeDecorator decorator) {
        return where(new LikeOperator(columnName, argument, decorator));
    }

    public SqlQuery ilike(String columnName, String argument) {
        return where(new CaseInsensitiveLikeOperator(columnName, argument));
    }

    public SqlQuery ilike(String columnName, String argument, LikeDecorator decorator) {
        return where(new CaseInsensitiveLikeOperator(columnName, argument, decorator));
    }
    
    public SqlQuery in(String columnName, Collection<?> arguments) {
        return where(new InOperator(columnName, arguments));
    }

    public SqlQuery in(String columnName, SqlQuery nestedQuery) {
        return where(new InOperator(columnName, nestedQuery));
    }
    
    public SqlQuery isNull(String columnName) {
        return whereSuffixUnary("is null", columnName);
    }

    public SqlQuery isNotNull(String columnName) {
        return whereSuffixUnary("is not null", columnName);
    }

    public SqlQuery exists(SqlQuery query) {
        return where(new ExistsOperator(query));
    }

    public SqlQuery where(String directCondition) {
        return where(new DirectConditionOperator(directCondition));
    }
    
    public SqlQuery where(SqlOperator expression) {
        whereOperators.add(expression);
        return this;
    }

    public SqlQuery where(String sql, Object... arguments) {
        whereOperators.add(new SqlOperator() {
            @Override
            public String getSql() {
                return sql;
            }

            @Override
            public List<Object> getArguments() {
                return Arrays.asList(arguments);
            }
        });
        return this;
    }

    /*
        Хелперы для having
     */

    public SqlQuery eqInHaving(String columnName, Object argument) {
        return havingBinary("=", columnName, argument);
    }

    public SqlQuery neqInHaving(String columnName, Object argument) {
        return havingBinary("<>", columnName, argument);
    }

    public SqlQuery geInHaving(String columnName, Object argument) {
        return havingBinary(">=", columnName, argument);
    }

    public SqlQuery gInHaving(String columnName, Object argument) {
        return havingBinary(">", columnName, argument);
    }

    public SqlQuery leInHaving(String columnName, Object argument) {
        return havingBinary("<=", columnName, argument);
    }

    public SqlQuery lInHaving(String columnName, Object argument) {
        return havingBinary("<", columnName, argument);
    }

    public SqlQuery likeHaving(String columnName, String argument) {
        return having(new LikeOperator(columnName, argument));
    }

    public SqlQuery likeHaving(String columnName, String argument, LikeDecorator decorator) {
        return having(new LikeOperator(columnName, argument, decorator));
    }

    public SqlQuery ilikeHaving(String columnName, String argument) {
        return having(new CaseInsensitiveLikeOperator(columnName, argument));
    }

    public SqlQuery ilikeHaving(String columnName, String argument, LikeDecorator decorator) {
        return having(new CaseInsensitiveLikeOperator(columnName, argument, decorator));
    }

    public SqlQuery inHaving(String columnName, List<Object> arguments) {
        return having(new InOperator(columnName, arguments));
    }

    public SqlQuery inHaving(String columnName, SqlQuery nestedQuery) {
        return having(new InOperator(columnName, nestedQuery));
    }

    public SqlQuery isNullHaving(String columnName) {
        return havingSuffixUnary("is null", columnName);
    }

    public SqlQuery isNotNullHaving(String columnName) {
        return havingSuffixUnary("is not null", columnName);
    }

    public SqlQuery having(SqlOperator expression) {
        havingOperators.add(expression);
        return this;
    }
    
    /*
        Хелперы для подзапросов
     */
    
    public SqlQuery with(String alias, SqlQuery query) {
        cteQueries.put(alias, query);
        return this;
    }
    
    public SqlQuery from(String alias, SqlQuery query) {
        nestedQueries.put(alias, query);
        return this;
    }
    
    /*
        Внутренние вспомогательные методы
     */
    
    private SqlQuery whereBinary(String operator, String columnName, Object argument) {
        return where(new BinaryOperator(operator, columnName, argument));
    }
    
    private SqlQuery wherePrefixUnary(String operator, String columnName) {
        return where(new PrefixUnaryOperator(operator, columnName));
    }

    private SqlQuery whereSuffixUnary(String operator, String columnName) {
        return where(new SuffixUnaryOperator(operator, columnName));
    }
    
    private SqlQuery havingBinary(String operator, String columnName, Object argument) {
        return having(new BinaryOperator(operator, columnName, argument));
    }

    private SqlQuery havingPrefixUnary(String operator, String columnName) {
        return having(new PrefixUnaryOperator(operator, columnName));
    }

    private SqlQuery havingSuffixUnary(String operator, String columnName) {
        return having(new SuffixUnaryOperator(operator, columnName));
    }
}
