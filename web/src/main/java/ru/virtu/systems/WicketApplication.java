package ru.virtu.systems;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.convert.converter.DateConverter;
import ru.virtu.systems.contract.ContractPage;
import ru.virtu.systems.contract.ContractsPage;
import ru.virtu.systems.resource.VSJS;
import ru.virtu.systems.util.converter.LocalDateConverter;

import java.time.LocalDate;
import java.util.Date;

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
        return HomePage.class;
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
        mountPage("/contractPage", ContractPage.class);
        mountPage("/homePage", HomePage.class);
    }

    @Override
    protected IConverterLocator newConverterLocator() {
        ConverterLocator locator = (ConverterLocator)super.newConverterLocator();
//        locator.set(Date.class, new LocalDateConverter());
//        locator.set(Date.class, new DateConverter());
        return locator;
    }
}
