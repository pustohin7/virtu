package ru.virtu.systems;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import ru.virtu.systems.base.HeaderPage;
import ru.virtu.systems.contract.ContractsPage;
import ru.virtu.systems.resource.VSJS;

/**
 * @author Alexey Pustohin
 */
public class WicketApplication extends WebApplication
{
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return ContractsPage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        // Базовые настройки
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getJavaScriptLibrarySettings().setJQueryReference(VSJS.jQuery().getReference());

        // Без этого не совсем работает верстка, т.к. теги викета могут мешать
        getMarkupSettings().setStripWicketTags(true);

        mountPages();
    }

    private void mountPages() {
        mountPage("/contractsPage", ContractsPage.class);
    }
}
