package design.design_pattern.structural;

/**
 * Adapter pattern works a bridge between 2 incompatible interfaces
 * The pattern involves:
 * - 1 class to join functionalities of independent or incompatible interfaces
 *
 * Advantages
 * - enables reusability and flexibility
 * - reduce complication of client class, which does not need to care about the implementation details of adapter
 *
 * Disadvantages
 * - all requests are forwarded, thus a slight increase in overhead
 * - sometimes many adaptations are required along an adapter chain to reach the required type
 *
 * In the following solution, we create MediaAdapter (adapter class)
 * that converts the incompatible interface AdvancedMediaPlayer (adaptee class)
 * into another interface MediaPlayer (target class)
 *
 * As you can see, we dont have to really implement the logic of play() in MediaPlayer from scratch
 * In stead, we delegate that task to AdvancedMediaPlayer.
 * Thus we can reuse AdvancedMediaPlayer code. User does not care what is underlying the MediaPlayer.
 */
public class AdapterDemo {
    interface MediaPlayer {
        void play(String audioType, String fileName);
    }

    interface AdvancedMediaPlayer {
        void playVlc(String fileName);
        void playMp4(String fileName);
    }

    static class AdvancedMediaPlayerImpl implements AdvancedMediaPlayer {
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

    //============================================================================

    static class MediaAdapter implements MediaPlayer {
        AdvancedMediaPlayer advancedMusicPlayer;
        public MediaAdapter(AdvancedMediaPlayer advancedMusicPlayer){
            this.advancedMusicPlayer = advancedMusicPlayer;
        }

        @Override
        public void play(String audioType, String fileName) {
            if(audioType.equalsIgnoreCase("vlc")){
                advancedMusicPlayer.playVlc(fileName);
            }
            else if(audioType.equalsIgnoreCase("mp4")){
                advancedMusicPlayer.playMp4(fileName);
            } else {
                System.out.println("Dont know how to play " + audioType);
            }
        }
    }
    public static void  main(String[] arg){
        MediaAdapter adapter = new MediaAdapter(new AdvancedMediaPlayerImpl());
        User user = new User(adapter);
        user.run("vlc", "Mirror");
        user.run("vlc2", "Mirror");
    }

}
