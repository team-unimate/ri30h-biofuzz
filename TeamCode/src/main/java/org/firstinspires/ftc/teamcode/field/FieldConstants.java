package org.firstinspires.ftc.teamcode.field;

import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;

public class FieldConstants {

    private static final PoseFactory P = PoseFactory.radians();

    public static final Pose BLUE_TARGET_POSE = P.of(0, 0, 0);

    public static final Pose RED_TARGET_POSE = P.of(0, 0, Math.PI);

    public static final Pose GO_TO_POSE_TARGET = P.of(0, 0, 0);

    public static final double HEADING_TOLERANCE_RAD = Math.toRadians(2.0);

    public static final double MAX_ALIGN_TURN_POWER = 0;
    public static final double ALIGN_kP = 0;
}