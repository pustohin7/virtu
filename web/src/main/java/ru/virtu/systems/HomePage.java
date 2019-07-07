package ru.virtu.systems;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import ru.virtu.systems.base.HeaderPage;

/**
 * @author Alexey Pustohin
 */
public class HomePage extends HeaderPage {
    public HomePage() {
    }

    public HomePage(PageParameters pageParameters) {
        super(pageParameters);
    }
}
