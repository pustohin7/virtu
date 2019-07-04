package ru.virtu.systems;


import org.apache.wicket.request.mapper.parameter.PageParameters;
import ru.virtu.systems.base.HeaderPage;

/**
 * @author Alexey Pustohin
 */
public class HomePage extends HeaderPage {
    public HomePage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

//        add(new CardOfferPanel("stockPanel").setVisible(isVisibleStockPanel()));
//        add(new FTCarousel("carousel", getCarouselItems()));
    }
}
