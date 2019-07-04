package ru.virtu.systems.dto.base;

/**
 * @author Alexey Pustohin
 */
public class CodeNameDto extends BaseEnity {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
