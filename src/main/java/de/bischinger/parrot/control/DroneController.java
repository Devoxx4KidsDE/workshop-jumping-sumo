package de.bischinger.parrot.control;

import de.devoxx4kids.dronecontroller.command.Command;
import de.devoxx4kids.dronecontroller.command.animation.Metronome;
import de.devoxx4kids.dronecontroller.command.animation.Ondulation;
import de.devoxx4kids.dronecontroller.command.animation.Slalom;
import de.devoxx4kids.dronecontroller.command.animation.SlowShake;
import de.devoxx4kids.dronecontroller.command.animation.Spin;
import de.devoxx4kids.dronecontroller.command.animation.SpinJump;
import de.devoxx4kids.dronecontroller.command.animation.SpinToPosture;
import de.devoxx4kids.dronecontroller.command.animation.Spiral;
import de.devoxx4kids.dronecontroller.command.animation.StopAnimation;
import de.devoxx4kids.dronecontroller.command.animation.Tap;
import de.devoxx4kids.dronecontroller.command.common.Disconnect;
import de.devoxx4kids.dronecontroller.command.movement.Jump;
import de.devoxx4kids.dronecontroller.command.movement.Pcmd;
import de.devoxx4kids.dronecontroller.command.multimedia.AudioTheme;
import de.devoxx4kids.dronecontroller.command.multimedia.VideoStreaming;
import de.devoxx4kids.dronecontroller.command.multimedia.Volume;
import de.devoxx4kids.dronecontroller.listener.BatteryListener;
import de.devoxx4kids.dronecontroller.listener.BatteryState;
import de.devoxx4kids.dronecontroller.listener.CriticalBatteryListener;
import de.devoxx4kids.dronecontroller.listener.OutdoorSpeedListener;
import de.devoxx4kids.dronecontroller.listener.PCMDListener;
import de.devoxx4kids.dronecontroller.listener.VideoListener;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * Controller to control the drone connected by the {@link DroneConnection}.
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class DroneController implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DroneConnection droneConnection;

    public DroneController(DroneConnection droneConnection) throws IOException {

        LOGGER.info("Creating DroneController");
        this.droneConnection = droneConnection;

        droneConnection.connect();
    }

    @Override
    public void close() throws Exception {

        droneConnection.sendCommand(Disconnect.disconnect());
    }


    public DroneController send(Command command) throws IOException {

        this.droneConnection.sendCommand(command);

        return this;
    }


    public DroneController pcmd(int speed, int degree) throws IOException {

        this.droneConnection.sendCommand(Pcmd.pcmd(speed, degree));

        return this;
    }


    public DroneController forward() throws IOException {

        pcmd(40, 0);

        return this;
    }


    public DroneController backward() throws IOException {

        pcmd(-40, 0);

        return this;
    }


    public DroneController left() throws IOException {

        left(90);

        return this;
    }


    public DroneController left(int degrees) throws IOException {

        pcmd(0, -degrees);

        return this;
    }


    public DroneController right(int degrees) throws IOException {

        pcmd(0, degrees);

        return this;
    }


    public DroneController right() throws IOException {

        right(90);

        return this;
    }


    public DroneController jump(Jump.Type jumpType) throws IOException {

        this.droneConnection.sendCommand(Jump.jump(jumpType));

        return this;
    }


    public DroneController jumpHigh() throws IOException {

        this.droneConnection.sendCommand(Jump.jump(Jump.Type.High));

        return this;
    }


    public DroneController jumpLong() throws IOException {

        this.droneConnection.sendCommand(Jump.jump(Jump.Type.Long));

        return this;
    }


    public DroneController stopAnimation() throws IOException {

        this.droneConnection.sendCommand(StopAnimation.stopAnimation());

        return this;
    }


    public DroneController spin() throws IOException {

        this.droneConnection.sendCommand(Spin.spin());

        return this;
    }


    public DroneController tap() throws IOException {

        this.droneConnection.sendCommand(Tap.tap());

        return this;
    }


    public DroneController slowShake() throws IOException {

        this.droneConnection.sendCommand(SlowShake.slowShake());

        return this;
    }


    public DroneController metronome() throws IOException {

        this.droneConnection.sendCommand(Metronome.metronome());

        return this;
    }


    public DroneController ondulation() throws IOException {

        this.droneConnection.sendCommand(Ondulation.ondulation());

        return this;
    }


    public DroneController spinJump() throws IOException {

        this.droneConnection.sendCommand(SpinJump.spinJump());

        return this;
    }


    public DroneController spinToPosture() throws IOException {

        this.droneConnection.sendCommand(SpinToPosture.spinToPosture());

        return this;
    }


    public DroneController spiral() throws IOException {

        this.droneConnection.sendCommand(Spiral.spiral());

        return this;
    }


    public DroneController slalom() throws IOException {

        this.droneConnection.sendCommand(Slalom.slalom());

        return this;
    }


    public DroneController addCriticalBatteryListener(Consumer<BatteryState> consumer) {

        droneConnection.addEventListener(CriticalBatteryListener.criticalBatteryListener(consumer));

        return this;
    }


    public DroneController addBatteryListener(Consumer<Byte> consumer) {

        droneConnection.addEventListener(BatteryListener.batteryListener(consumer));

        return this;
    }


    public DroneController addPCMDListener(Consumer<String> consumer) {

        droneConnection.addEventListener(PCMDListener.pcmdlistener(consumer));

        return this;
    }


    public DroneController addOutdoorSpeedListener(Consumer<String> consumer) {

        droneConnection.addEventListener(OutdoorSpeedListener.outdoorSpeedListener(consumer));

        return this;
    }


    public AudioController audio() {

        return new AudioController(this);
    }


    public VideoController video() {

        return new VideoController(this);
    }

    public class AudioController {

        private final DroneController droneController;

        public AudioController(DroneController droneController) {

            this.droneController = droneController;
        }

        public AudioController theme(AudioTheme.Theme theme) throws IOException {

            droneConnection.sendCommand(AudioTheme.audioTheme(theme));

            return this;
        }


        public AudioController volume(int volume) throws IOException {

            droneConnection.sendCommand(Volume.volume(volume));

            return this;
        }


        public AudioController mute() throws IOException {

            volume(0);

            return this;
        }


        public AudioController unmute() throws IOException {

            volume(100);

            return this;
        }


        public DroneController drone() {

            return droneController;
        }
    }

    public class VideoController {

        private final DroneController droneController;

        public VideoController(DroneController droneController) {

            this.droneController = droneController;
        }

        public VideoController enableVideo() throws IOException {

            droneConnection.addEventListener(VideoListener.videoListener());
            droneConnection.sendCommand(VideoStreaming.enableVideoStreaming());

            return this;
        }


        public VideoController disableVideo() throws IOException {

            droneConnection.sendCommand(VideoStreaming.disableVideoStreaming());

            return this;
        }


        public DroneController drone() {

            return droneController;
        }
    }
}