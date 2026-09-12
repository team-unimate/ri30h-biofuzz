package org.firstinspires.ftc.teamcode.commands.field;

import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.field.Field;

public class GoToPoseCommand extends CommandBase {

    private final Field field;
    private final Pose target;

    public GoToPoseCommand(Field field, Pose target) {
        this.field = field;
        this.target = target;
        addRequirements(field);
    }

    @Override
    public void initialize() {
        field.goToPose(target);
    }

    @Override
    public boolean isFinished() {
        return field.atTarget();
    }

    @Override
    public void end(boolean interrupted) {
        field.stop();
    }
}
