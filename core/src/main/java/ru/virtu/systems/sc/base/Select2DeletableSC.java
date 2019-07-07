package ru.virtu.systems.sc.base;

/**
 * @author Alexey Pustohin
 */
public class Select2DeletableSC extends Select2SC implements IDeletableSC {

    private Boolean showDeleted = false;

    @Override
    public Boolean isShowDeleted() {
        return showDeleted;
    }

    public void setShowDeleted(Boolean showDeleted) {
        this.showDeleted = showDeleted;
    }
}
