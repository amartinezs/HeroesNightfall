package com.Menu;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

/**
 * Created by Albert on 08/05/2015.
 */
public class GameButton {

    private String buttonId;
    private CompositeItem button;
    private SimpleButtonScript buttonScript;


    public GameButton(String buttonId, CompositeItem menu){
        this.buttonId = buttonId;
        this.button = menu.getCompositeById(buttonId);
        this.buttonScript = new SimpleButtonScript();
        this.buttonScript.init(this.button);
    }


    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public CompositeItem getButton() {
        return button;
    }

    public void setButton(CompositeItem button) {
        this.button = button;
    }

    public SimpleButtonScript getButtonScript() {
        return buttonScript;
    }

    public void setButtonScript(SimpleButtonScript buttonScript) {
        this.buttonScript = buttonScript;
    }
}
