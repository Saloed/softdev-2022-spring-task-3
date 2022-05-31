package javafxTornado

import javafx.application.Application
import javafx.scene.control.ButtonBar
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.icons.add(Image("snakeIcon.png"))
        val dialogue = ChooseMode()
        val result = dialogue.showAndWait()
        if (result.isPresent && result.get().buttonData == ButtonBar.ButtonData.OK_DONE) {
            twoPlayersCondition = dialogue.twoPlayers
            eatToLiveCondition = dialogue.eatToLive
            super.start(stage)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}