package org.firstinspires.ftc.teamcode.field;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.field.FieldConstants.*;

public class AutoAligner extends SubsystemBase {

    private final Follower follower;
    private final Telemetry telemetry;

    private Pose targetPose;

    private boolean active = false;

    public AutoAligner(Follower follower) {
        this.follower = follower;
        this.telemetry = FtcDashboard.getInstance().getTelemetry();
        this.targetPose = BLUE_TARGET_POSE;
    }

    public void setTarget(Pose target) {
        this.targetPose = target;
    }

    public void activate() {
        active = true;
    }

    public void stop() {
        active = false;
    }

    public double computeTurnPower() {
        if (!active) return 0.0;

        double error = getHeadingError();
        double power = ALIGN_kP * error;

        power = Math.max(-MAX_ALIGN_TURN_POWER, Math.min(MAX_ALIGN_TURN_POWER, power));

        return power;
    }

    public boolean isAligned() {
        return Math.abs(getHeadingError()) < HEADING_TOLERANCE_RAD;
    }

    public boolean isActive() {
        return active;
    }

    private double getHeadingError() {
        Pose current = follower.pose();

        double dx = targetPose.x() - current.x();
        double dy = targetPose.y() - current.y();

        double desiredHeading = Math.atan2(dy, dx);
        double currentHeading = current.heading();

        // Normalize error to [-π, π]
        double error = desiredHeading - currentHeading;
        while (error >  Math.PI) error -= 2 * Math.PI;
        while (error < -Math.PI) error += 2 * Math.PI;

        return error;
    }

    @Override
    public void periodic() {
        Pose current = follower.pose();
        telemetry.addData("[AutoAligner] active",        active);
        telemetry.addData("[AutoAligner] target X",      targetPose.x());
        telemetry.addData("[AutoAligner] target Y",      targetPose.y());
        telemetry.addData("[AutoAligner] robot X",       current.x());
        telemetry.addData("[AutoAligner] robot Y",       current.y());
        telemetry.addData("[AutoAligner] heading error (deg)",
                Math.toDegrees(getHeadingError()));
        telemetry.addData("[AutoAligner] aligned",       isAligned());
    }
}