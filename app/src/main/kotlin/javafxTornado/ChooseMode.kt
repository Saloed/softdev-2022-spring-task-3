package javafxTornado

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import tornadofx.*

class ChooseMode : Dialog<ButtonType>() {
    private val eatToLiveString = SimpleStringProperty()
    val eatToLive: Boolean get() = eatToLiveString.value == "EAT TO LIVE"

    private val twoPlayersString = SimpleStringProperty()
    val twoPlayers: Boolean get() = twoPlayersString.value == "2 PLAYERS"
    init {
        title = "SNAKE GAME"
        with(dialogPane) {
            headerText = "Choose mode"
            buttonTypes.add(ButtonType("Start Game", ButtonBar.ButtonData.OK_DONE))
            buttonTypes.add(ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE))
            content = vbox {
                togglegroup {
                    bind(eatToLiveString)
                    bind(twoPlayersString)
                    radiobutton("Classic mode"){
                        isSelected = true
                    }
                    radiobutton("EAT TO LIVE")
                    radiobutton("2 PLAYERS")
                }
            }
        }
    }
}