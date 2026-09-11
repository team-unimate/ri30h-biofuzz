package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.field.AlignToTargetCommand;
import org.firstinspires.ftc.teamcode.commands.field.StopAlignerCommand;
import org.firstinspires.ftc.teamcode.field.AutoAligner;
import org.firstinspires.ftc.teamcode.field.FieldConstants;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class TeleOpMain extends CommandOpMode {

    private Follower follower;
    private AutoAligner autoAligner;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        follower = Constants.create(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        autoAligner = new AutoAligner(follower);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(new AlignToTargetCommand(autoAligner, FieldConstants.BLUE_TARGET_POSE))
                .whenInactive(new StopAlignerCommand(autoAligner));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(new AlignToTargetCommand(autoAligner, FieldConstants.RED_TARGET_POSE))
                .whenInactive(new StopAlignerCommand(autoAligner));

        register(autoAligner);

    }

    @Override
    public void run() {
        super.run();


        double turn;
        if (autoAligner.isActive()) {
            turn = autoAligner.computeTurnPower();
        } else {
            turn = -gamepad1.right_stick_x;
        }

        follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, turn);
        follower.update();
    }
}