package ru.virtu.systems.resource;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import ru.virtu.systems.base.BasePage;

/**
 * @author Alexey Pustohin
 */
public class BaseResourceHelper {
    protected static JavaScriptResourceReference jsRef(String name) {
        return new JavaScriptResourceReference(BasePage.class, "../resource/js/" + name, null, null, null);
    }

    protected static CssResourceReference cssRef(String name) {
        return new CssResourceReference(BasePage.class, "../resource/css/" + name, null, null, null);
    }

}
