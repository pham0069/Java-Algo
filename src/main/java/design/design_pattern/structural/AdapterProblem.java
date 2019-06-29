package design.design_pattern.structural;

/**
 *
 * Given the following scenario:
 * 1. We have a client library that defines User requiring MediaPlayer
 * but did not implement the MediaPlayer interface concretely
 * 2. In our code base, we already develop AdvancedMediaPlayer interface and AdvancedMediaPlayerImpl implementation
 * that is capable of playing vlc and mp4 files
 *
 * In terms of functionality, AdvancedMediaPlayer can be used in place of MediaPlayer
 * But in terms of interface, AdvancedMediaPlayer and MediaPlayer are not compatible, thus we cannot directly use
 * AdvancedMediaPlayer to plug-in User code.
 *
 * The problems are:
 * 1. How can we use our AdvancedMediaPlayer interface to work with User class?
 * 2. How can we reuse AdvancedMediaPlayerImpl code, which is not a MediaPlayer, to provide for User's requirements?
 *
 *
 */
public class AdapterProblem {
    interface MediaPlayer {
        void play(String audioType, String fileName);
    }
    interface AdvancedMediaPlayer {
        void playVlc(String fileName);
        void playMp4(String fileName);
    }

    static class AdvancedMediaPlayerImpl implements AdvancedMediaPlayer{
        @Override
        public void playVlc(String fileName) {
            System.out.println("Playing vlc file. Name: "+ fileName);
        }
        @Override
        public void playMp4(String fileName) {
            System.out.println("Playing mp4 file. Name: "+ fileName);
        }
    }

    static class User {
        private MediaPlayer mediaPlayer;
        User(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }
        void run(String audioType, String fileName) {
            mediaPlayer.play(audioType, fileName);
        }

    }
}
