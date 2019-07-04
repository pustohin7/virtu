package ru.virtu.systems.base;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.MetaDataHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import ru.virtu.systems.resource.VSJS;

/**
 * @author Alexey Pustohin
 */
public class BasePage extends WebPage {

    public BasePage() {
    }

    public BasePage(PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // css
        response.render(VSJS.bootstrap());

   /*     response.render(FTCSS.faCommon());
        response.render(FTCSS.faSolid());
        response.render(FTCSS.faRegular());
        response.render(FTCSS.faBrands());
        response.render(FTCSS.faV4Shims());

        response.render(FTCSS.ftStyles());*/

        // js
        response.render(VSJS.jQuery());
        response.render(VSJS.bootstrap());
//        response.render(VSJS.ftUtils());

        // favicons
/*        response.render(new FaviconHeaderItem("icon", "32x32", FTImages.favicon32()));
        response.render(new FaviconHeaderItem("icon", "16x16", FTImages.favicon16()));
        response.render(new FaviconHeaderItem("apple-touch-icon", "180x180", FTImages.favicon180()));*/
    }
}
