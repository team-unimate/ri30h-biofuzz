package org.firstinspires.ftc.teamcode.commands.field;

import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.field.AutoAligner;

public class AlignToTargetCommand extends CommandBase {

    private final AutoAligner autoAligner;
    private final Pose target;

    public AlignToTargetCommand(AutoAligner autoAligner, Pose target) {
        this.autoAligner = autoAligner;
        this.target = target;
        addRequirements(autoAligner);
    }

    @Override
    public void initialize() {
        autoAligner.setTarget(target);
        autoAligner.activate();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}