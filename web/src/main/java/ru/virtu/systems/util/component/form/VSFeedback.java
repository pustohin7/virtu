package ru.virtu.systems.util.component.form;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Alexey Pustohin
 */
public class VSFeedback extends FeedbackPanel {
    public VSFeedback(String id) {
        super(id);
        setOutputMarkupPlaceholderTag(true);
        setMarkupId(id);
    }
}
