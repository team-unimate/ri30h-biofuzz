package org.firstinspires.ftc.teamcode.commands.field;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.field.AutoAligner;

public class StopAlignerCommand extends CommandBase {

    private final AutoAligner autoAligner;

    public StopAlignerCommand(AutoAligner autoAligner) {
        this.autoAligner = autoAligner;
        addRequirements(autoAligner);
    }

    @Override
    public void initialize() {
        autoAligner.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}