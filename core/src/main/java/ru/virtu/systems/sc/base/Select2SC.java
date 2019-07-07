package ru.virtu.systems.sc.base;

import java.util.Collection;

/**
 * @author Alexey Pustohin
 */
public class Select2SC extends BaseSC implements ITermSC {

    private String term;
    private Collection<String> ids;

    @Override
    public String getTerm() {
        return term;
    }

    @Override
    public void setTerm(String term) {
        this.term = term;
    }

    public Collection<String> getIds() {
        return ids;
    }

    public void setIds(Collection<String> ids) {
        this.ids = ids;
    }

}
