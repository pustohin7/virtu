package ru.virtu.systems.util.component.paging;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import ru.virtu.systems.util.component.link.VSAjaxLink;
import ru.virtu.systems.util.functional.SerializableSupplier;
import ru.virtu.systems.util.model.LongPPModel;

/**
 * @author Alexey Pustohin
 */
public class PagingPanel<T extends Component & IPageableItems> extends Panel {

    private T pageable;
    private LongPPModel pageNumberModel;

    public PagingPanel(String id, T pageable) {
        super(id);
        this.pageable = pageable;

        setOutputMarkupId(true);
        setMarkupId(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageNumberModel = new LongPPModel(getPage(), "pg", 0L);
        pageable.setCurrentPage(pageNumberModel.getObject());
    }

    @Override
    protected void onBeforeRender() {
        addOrReplace(new PageSwitcherLink(
                "prevPage", "<i class=\"fas fa-chevron-left\"></i>",
                () -> Math.max(pageNumberModel.getObject() - 1, 0))
        );
        addOrReplace(new PageSwitcherLink(
                "nextPage", "<i class=\"fas fa-chevron-right\"></i>",
                () -> Math.min(pageNumberModel.getObject() + 1, (int) pageable.getPageCount() - 1)
        ));

        RepeatingView pages = new RepeatingView("pages");
        addOrReplace(pages);

        long pageCount = pageable.getPageCount();

        if (pageCount <= 11) {
            // Если страниц 11 и меньше, то просто рисуем все
            addPageLinks(0, pageCount - 1, pages);
        } else {
            // Иначе смотрим, где мы стоим
            long currentPage = pageNumberModel.getObject();

            if (currentPage <= 5) {
                // Если стоим среди первых 6 страниц, рисуем так: 1 2 3 4 5 6 7 8 9 ... 50
                addPageLinks(0, 8, pages);
                addPageLinks(-1, -1, pages);
                addPageLinks(pageCount - 1, pageCount - 1, pages);

            } else if (pageCount - currentPage <= 6) {
                // Если стоим среди последних 6 страниц, рисуем так: 1 ... 42 43 44 45 46 47 48 49 50
                addPageLinks(0, 0, pages);
                addPageLinks(-1, -1, pages);
                addPageLinks(pageCount - 8, pageCount - 1, pages);

            } else {
                // Иначе рисуем так: 1 ... 23 24 25 26 27 28 29 .. 50
                addPageLinks(0, 0, pages);
                addPageLinks(-1, -1, pages);
                addPageLinks(currentPage - 3, currentPage + 3, pages);
                addPageLinks(-2, -2, pages);
                addPageLinks(pageCount - 1, pageCount - 1, pages);
            }
        }

        super.onBeforeRender();
    }

    private void addPageLinks(long from, long to, RepeatingView pages) {
        for (long i = from; i <= to; i++) {
            final long finalI = i;

            WebMarkupContainer liContainer = new WebMarkupContainer(pages.newChildId());
            String label = i >= 0 ? " " + (i + 1) : "...";
            liContainer.add(new PageSwitcherLink("pageLink", label, () -> finalI) {
                @Override
                protected void onInitialize() {
                    super.onInitialize();
                    setMarkupId("page" + finalI);
                }
            });
            pages.add(liContainer);
        }
    }

    protected Component[] getComponentsToUpdate() {
        return new Component[]{pageable, this};
    }

    protected boolean scrollToTopAfterPageSwitch() {
        return false;
    }

    private class PageSwitcherLink extends VSAjaxLink {
        private SerializableSupplier<Long> pageNumberSupplier;

        public PageSwitcherLink(String id, String label, SerializableSupplier<Long> pageNumberSupplier) {
            super(id);
            this.pageNumberSupplier = pageNumberSupplier;

            add(new Label("label", label).setEscapeModelStrings(false));
        }

        @Override
        protected void onBeforeRender() {
            super.onBeforeRender();

            long newPageNumber = pageNumberSupplier.get();
            setEnabled(newPageNumber >= 0 && newPageNumber < pageable.getPageCount() && newPageNumber != pageNumberModel.getObject());
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            long newPageNumber = pageNumberSupplier.get();

            pageable.setCurrentPage(newPageNumber);
            pageNumberModel.setObject(newPageNumber);
            target.add(getComponentsToUpdate());

            if (scrollToTopAfterPageSwitch()) {
                target.appendJavaScript("Wicket.FTUtils.scrollToHeader();");
            }
        }
    }
}
