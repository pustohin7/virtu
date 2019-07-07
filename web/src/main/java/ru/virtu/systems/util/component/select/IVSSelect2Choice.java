package ru.virtu.systems.util.component.select;

import com.vaynberg.wicket.select2.Settings;

/**
 * @author Alexey Pustohin
 */
interface IVSSelect2Choice {

    Settings getSettings();

    boolean isRequired();

    default void disableMarkupEscaping() {
        getSettings().setEscapeMarkup("function(markup) { return markup; }");
    }

    default void vsInit() {
        getSettings().setMinimumResultsForSearch(10);
        getSettings().setDropdownCssClass("vs-select2-drop");
        getSettings().setCloseOnSelect(true);

        if (!isRequired())  {
            if (getSettings().getAllowClear() == null) {
                getSettings().setAllowClear(true);
            }
        }  else {
            getSettings().setAllowClear(false);
        }
    }

}
