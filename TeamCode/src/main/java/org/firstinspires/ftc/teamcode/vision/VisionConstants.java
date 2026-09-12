package org.firstinspires.ftc.teamcode.vision;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VisionConstants {

    public static final String HM_LIMELIGHT = "limelight";

    public static final int PIPELINE_APRILTAG = 0;

    public static final Set<Integer> RED_SCORING_CELL_TAGS = unmodifiableSetOf(30, 31, 32, 33);

    public static final Set<Integer> RED_AUDIENCE_CELL_TAGS = unmodifiableSetOf(34, 35, 36, 37);


    public static final Set<Integer> BLUE_AUDIENCE_CELL_TAGS = unmodifiableSetOf(38, 39, 40, 41);

    public static final Set<Integer> BLUE_SCORING_CELL_TAGS = unmodifiableSetOf(42, 43, 44, 45);

    public static final Set<Integer> ALL_FIELD_TAG_IDS = unmodifiableSetOf(
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);

    public static final boolean SHOOT_WHEN_CELL_TAGS_VISIBLE = true;

    private static Set<Integer> unmodifiableSetOf(Integer... ids) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ids)));
    }

    private VisionConstants() {}
}