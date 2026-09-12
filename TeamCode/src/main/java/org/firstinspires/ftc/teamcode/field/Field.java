package org.firstinspires.ftc.teamcode.field;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import static com.pedropathing.api.Paths.line;

public class Field extends SubsystemBase {

    private final Follower follower;

    private boolean active = false;

    public Field(Follower follower) {
        this.follower = follower;
    }

    public void goToPose(Pose target) {
        Path path = line(follower.pose(), target).constant(target.heading());
        follower.follow(path);
        active = true;
    }

    public void stop() {
        follower.hold(follower.pose());
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean atTarget() {
        return follower.atParametricEnd();
    }
}
