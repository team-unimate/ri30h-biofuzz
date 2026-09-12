package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.field.AlignToTargetCommand;
import org.firstinspires.ftc.teamcode.commands.field.GoToPoseCommand;
import org.firstinspires.ftc.teamcode.commands.field.StopAlignerCommand;
import org.firstinspires.ftc.teamcode.field.AutoAligner;
import org.firstinspires.ftc.teamcode.field.Field;
import org.firstinspires.ftc.teamcode.field.FieldConstants;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class TeleOpMain extends CommandOpMode {

    private Follower follower;
    private AutoAligner autoAligner;
    private Field field;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        follower = Constants.create(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        autoAligner = new AutoAligner(follower);
        field = new Field(follower);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(new AlignToTargetCommand(autoAligner, FieldConstants.BLUE_TARGET_POSE))
                .whenInactive(new StopAlignerCommand(autoAligner));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(new AlignToTargetCommand(autoAligner, FieldConstants.RED_TARGET_POSE))
                .whenInactive(new StopAlignerCommand(autoAligner));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whenActive(new GoToPoseCommand(field, FieldConstants.GO_TO_POSE_TARGET));

        register(autoAligner, field);

    }

    @Override
    public void run() {
        super.run();


        if (!field.isActive()) {
            double turn;
            if (autoAligner.isActive()) {
                turn = autoAligner.computeTurnPower();
            } else {
                turn = -gamepad1.right_stick_x;
            }

            follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, turn);
        }

        follower.update();
    }
}