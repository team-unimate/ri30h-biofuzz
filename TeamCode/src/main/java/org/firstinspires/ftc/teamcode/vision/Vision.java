package org.firstinspires.ftc.teamcode.vision;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.firstinspires.ftc.teamcode.vision.VisionConstants.*;

public class Vision extends SubsystemBase {

    private final Limelight3A limelight;
    private final Telemetry telemetry;

    private LLResult latestResult;

    public Vision(HardwareMap hardwareMap) {
        this.telemetry = FtcDashboard.getInstance().getTelemetry();

        limelight = hardwareMap.get(Limelight3A.class, HM_LIMELIGHT);
        limelight.pipelineSwitch(PIPELINE_APRILTAG);
        limelight.start();
    }

    public boolean hasTarget() {
        return latestResult != null && latestResult.isValid();
    }

    public List<Integer> getVisibleFieldTagIds() {
        List<Integer> ids = new ArrayList<>();
        if (!hasTarget()) return ids;
        for (LLResultTypes.FiducialResult fr : latestResult.getFiducialResults()) {
            int id = fr.getFiducialId();
            if (ALL_FIELD_TAG_IDS.contains(id)) ids.add(id);
        }
        return ids;
    }

    public Double getDistanceToTag(int tagId) {
        if (!hasTarget()) return null;

        for (LLResultTypes.FiducialResult fr : latestResult.getFiducialResults()) {
            if (fr.getFiducialId() == tagId) {
                return distanceFromPose(fr.getCameraPoseTargetSpace());
            }
        }
        return null;
    }

    public TagDistance getClosestTag() {
        if (!hasTarget()) return null;

        TagDistance closest = null;
        for (LLResultTypes.FiducialResult fr : latestResult.getFiducialResults()) {
            Double d = distanceFromPose(fr.getCameraPoseTargetSpace());
            if (d == null) continue;
            if (closest == null || d < closest.distanceInches) {
                closest = new TagDistance(fr.getFiducialId(), d);
            }
        }
        return closest;
    }

    private Double distanceFromPose(Pose3D cameraInTagSpace) {
        if (cameraInTagSpace == null) return null;
        Position p = cameraInTagSpace.getPosition().toUnit(DistanceUnit.INCH);
        return Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
    }


    public boolean isCellTagVisible(Set<Integer> clusterTagIds) {
        if (!hasTarget()) return false;
        for (LLResultTypes.FiducialResult fr : latestResult.getFiducialResults()) {
            if (clusterTagIds.contains(fr.getFiducialId())) return true;
        }
        return false;
    }
    public Double getDistanceToCell(Set<Integer> clusterTagIds) {
        if (!hasTarget()) return null;

        Double best = null;
        for (LLResultTypes.FiducialResult fr : latestResult.getFiducialResults()) {
            if (!clusterTagIds.contains(fr.getFiducialId())) continue;
            Double d = distanceFromPose(fr.getCameraPoseTargetSpace());
            if (d == null) continue;
            if (best == null || d < best) best = d;
        }
        return best;
    }

    public boolean canShootCell(Set<Integer> clusterTagIds) {
        boolean visible = isCellTagVisible(clusterTagIds);
        return SHOOT_WHEN_CELL_TAGS_VISIBLE == visible;
    }

    @Override
    public void periodic() {
        latestResult = limelight.getLatestResult();

        telemetry.addData("[Vision] hasTarget", hasTarget());
        if (hasTarget()) {
            telemetry.addData("[Vision] visible tag IDs", getVisibleFieldTagIds());
            TagDistance closest = getClosestTag();
            if (closest != null) {
                telemetry.addData("[Vision] closest tag", closest.tagId);
                telemetry.addData("[Vision] distance (in)", closest.distanceInches);
            }
        }

        telemetry.addData("[Vision] RED_FAR visible", isCellTagVisible(RED_SCORING_CELL_TAGS));
        telemetry.addData("[Vision] RED_AUDIENCE visible", isCellTagVisible(RED_AUDIENCE_CELL_TAGS));
        telemetry.addData("[Vision] BLUE_AUDIENCE visible", isCellTagVisible(BLUE_AUDIENCE_CELL_TAGS));
        telemetry.addData("[Vision] BLUE_FAR visible", isCellTagVisible(BLUE_SCORING_CELL_TAGS));
    }

    public static class TagDistance {
        public final int tagId;
        public final double distanceInches;

        TagDistance(int tagId, double distanceInches) {
            this.tagId = tagId;
            this.distanceInches = distanceInches;
        }
    }
}