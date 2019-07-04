package ru.virtu.systems.resource;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author Alexey Pustohin
 */
public class VSJS extends BaseResourceHelper {

    private static final JavaScriptResourceReference BOOTSTRAP = jsRef("bootstrap/bootstrap.js");

    private static final JavaScriptResourceReference JQUERY = jsRef("jquery/jquery-3.3.1.js");

    private static final JavaScriptResourceReference FT_UTILS = jsRef("ft/FTUtils.js");

    public static JavaScriptReferenceHeaderItem bootstrap() {
        return JavaScriptHeaderItem.forReference(BOOTSTRAP);
    }

    public static JavaScriptReferenceHeaderItem jQuery() {
        return JavaScriptHeaderItem.forReference(JQUERY);
    }

    public static JavaScriptReferenceHeaderItem ftUtils() {
        return JavaScriptHeaderItem.forReference(FT_UTILS);
    }

}