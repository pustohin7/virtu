package ru.virtu.systems.base;

import org.apache.wicket.Page;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ru.virtu.systems.HomePage;
import ru.virtu.systems.contract.ContractsPage;
import ru.virtu.systems.util.model.LambdaModel;

/**
 * @author Alexey Pustohin
 */
public class HeaderPage extends BasePage {

    public HeaderPage() {
    }

    public HeaderPage(PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new HighlightableLi("home", HomePage.class));
        add(new HighlightableLi("contractsPage", ContractsPage.class));
    }

    private class HighlightableLi extends WebMarkupContainer {
        public HighlightableLi(String id, Class<? extends Page> pageClass) {
            super(id);

            add(new BookmarkablePageLink<Void>("link", pageClass));

            add(new AttributeAppender("class", new LambdaModel<>(() -> getHighlightClass(pageClass))));
        }

        private String getHighlightClass(Class<? extends Page> pageClass) {
            Page page = getPage();
            if (page instanceof HeaderPage) {
                Class<? extends Page> currentPageClass = ((HeaderPage) page).getHeaderPage();
                if (currentPageClass.isAssignableFrom(pageClass)) {
                    return "vs-selected";
                }
            }
            return null;
        }
    }

    public Class<? extends Page> getHeaderPage() {
        return getClass();
    }

}
