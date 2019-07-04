package ru.virtu.systems.base;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
    }

    public Class<? extends Page> getHeaderPage() {
        return getClass();
    }

}
