package ru.virtu.systems.resource;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * @author Alexey Pustohin
 */
public class VSCSS extends BaseResourceHelper {

    private static final CssResourceReference FT_STYLES = cssRef("ft.css");
    private static final CssResourceReference BOOTSTRAP = cssRef("bootstrap/bootstrap.css");

    private static final CssResourceReference FA_COMMON = cssRef("font-awesome/fontawesome.css");
    private static final CssResourceReference FA_SOLID = cssRef("font-awesome/solid.css");
    private static final CssResourceReference FA_REGULAR = cssRef("font-awesome/regular.css");
    private static final CssResourceReference FA_BRANDS = cssRef("font-awesome/brands.css");
    private static final CssResourceReference FA_V4_SHIMS = cssRef("font-awesome/v4-shims.css");


    public static CssReferenceHeaderItem ftStyles() {
        return CssReferenceHeaderItem.forReference(FT_STYLES);
    }

    public static CssReferenceHeaderItem bootstrap() {
        return CssReferenceHeaderItem.forReference(BOOTSTRAP);
    }

    public static CssReferenceHeaderItem faCommon() {
        return CssReferenceHeaderItem.forReference(FA_COMMON);
    }

    public static CssReferenceHeaderItem faSolid() {
        return CssReferenceHeaderItem.forReference(FA_SOLID);
    }

    public static CssReferenceHeaderItem faRegular() {
        return CssReferenceHeaderItem.forReference(FA_REGULAR);
    }

    public static CssReferenceHeaderItem faBrands() {
        return CssReferenceHeaderItem.forReference(FA_BRANDS);
    }

    public static CssReferenceHeaderItem faV4Shims() {
        return CssReferenceHeaderItem.forReference(FA_V4_SHIMS);
    }

}
