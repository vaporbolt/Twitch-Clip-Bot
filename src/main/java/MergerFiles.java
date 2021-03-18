
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.builder.AbstractFFmpegStreamBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Seth Roper
 * 
 *     this class utilizes a wrapper for ffmpeg, which must be installed
 *     previously under the c drive. this class encompasses several static
 *     methods which deal with video data within file directories
 *
 */
public class MergerFiles {

    public static void merge(File file, File text, File output)
            throws FileNotFoundException, IOException {

        try {
            // ffmpeg should be installed under the c drive.
            FFmpeg ffmpeg = new FFmpeg("C:\\FFmpeg\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("C:\\FFmpeg\\bin\\ffprobe.exe");

            FFmpegProbeResult probeResult = ffprobe.probe(
                    file.getAbsolutePath());
            FFmpegFormat format = probeResult.getFormat();

            FFmpegStream stream = probeResult.getStreams().get(0);

            FFmpegBuilder builder = new FFmpegBuilder().setFormat(
                    "mp4").setInput(text.getAbsolutePath()).setFormat(
                            "concat").addExtraArgs("-safe",
                                    "0").overrideOutputFiles(true) // Override
                                                                   // the output
                                                                   // if it
                                                                   // exists
                    .addOutput(output.getAbsolutePath()) // Filename for the
                                                         // destination
                    // .setTargetSize(250_000) // Aim for a 250KB file
                    .disableSubtitle() // No subtiles

                    .setAudioChannels(2) // Mono audio
                    .setAudioCodec("aac") // using the aac codec
                    .setAudioSampleRate(48_000) // at 48KHz
                    .setAudioBitRate(163000) // at 32 kbit/s

                    .setVideoCodec("mpeg4") // Video using x264
                    .setVideoFrameRate(60, 1) // at 24 frames per second
                    .setVideoBitRate(12000000).setVideoResolution(1920, 1080) // at
                                                                              // 640x480
                                                                              // resolution
                    .setFormat("mp4").setStrict(
                            FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg
                                                               // to use
                                                               // experimental
                                                               // specs
                    .done();

            builder.setVerbosity(FFmpegBuilder.Verbosity.DEBUG);

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // run a two pass job to ensure video higher quality.)
            executor.createTwoPassJob(builder).run();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * @param file
     * @throws IOException
     */
    public static void resize(File file) throws IOException {
        File[] directoryListing = file.listFiles();

        int i = 0;
        for (File blah : directoryListing) {

            FFmpeg ffmpeg = new FFmpeg("C:\\FFmpeg\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("C:\\FFmpeg\\bin\\ffprobe.exe");

            FFmpegBuilder builder = new FFmpegBuilder()

                    .setInput(blah.getAbsolutePath())// Filename, or a
                                                     // FFmpegProbeResult
                    .addInput(blah.getAbsolutePath())

                    .overrideOutputFiles(true) // Override the output if it
                                               // exists

                    .addOutput(
                            "C:\\\\Users\\\\john Chastanet\\\\Downloads\\\\Output2\\"
                                    + i + ".mp4")   // Filename for the
                                                    // destination
                    .setFormat("mp4")        // Format is inferred from
                                             // filename, or can be set
                    // .setTargetSize(250_000) // Aim for a 250KB file

                    // .disableSubtitle() // No subtiles

                    .setAudioChannels(2)         // Stereo audio
                    .setAudioCodec("aac")        // using the aac codec
                    .setAudioSampleRate(48_000)  // at 48KHz
                    .setAudioBitRate(163000)      // at 163 kbit/s
                    .setVideoCodec("mpeg4") // Video using mpeg4
                    .setVideoFrameRate(60, 1)     // at 60 frames per second
                    .setVideoResolution(1920, 1080) // at 1920x1080 resolution

                    .setVideoBitRate(12000000)
                    // .setComplexVideoFilter("scale=320:trunc(ow/a/2)*2")
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow
                                                                  // FFmpeg to
                                                                  // use
                                                                  // experimental
                                                                  // specs
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // Run a two pass job for higher quality. 
            executor.createTwoPassJob(builder).run();
            i++;
        }

    }

    public static void resizeProduct(File file) throws IOException {
        File[] directoryListing = file.listFiles();

        int i = 0;
        for (File blah : directoryListing) {

            FFmpeg ffmpeg = new FFmpeg("C:\\FFmpeg\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("C:\\FFmpeg\\bin\\ffprobe.exe");

            FFmpegBuilder builder = new FFmpegBuilder()

                    .setInput(blah.getAbsolutePath())// Filename, or a
                                                     // FFmpegProbeResult
                    .addInput(blah.getAbsolutePath())

                    .overrideOutputFiles(true) // Override the output if it
                                               // exists

                    .addOutput("C:\\Users\\johnn\\Desktop\\Overwatch clips\\new"
                            + i + ".mp4")   // Filename for the destination
                    .setFormat("mp4")        // Format is inferred from
                                             // filename, or can be set
                    // .setTargetSize(250_000) // Aim for a 250KB file

                    // .disableSubtitle() // No subtiles

                    .setAudioChannels(1)         // Mono audio
                    .setAudioCodec("aac")        // using the aac codec
                    .setAudioSampleRate(48_000)  // at 48KHz
                    .setAudioBitRate(163000)      // at 32 kbit/s
                    .setVideoCodec("mpeg4") // Video using x264
                    .setVideoFrameRate(60, 1)     // at 24 frames per second
                    .setVideoResolution(1920, 1080) // at 640x480 resolution

                    .setVideoBitRate(12000000)
                    // .setComplexVideoFilter("scale=320:trunc(ow/a/2)*2")

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow
                                                                  // FFmpeg to
                                                                  // use
                                                                  // experimental
                                                                  // specs
                    .done();

            
            
            
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // Run a one-pass encode
            executor.createTwoPassJob(builder).run();
            i++;
            // delete file after

        }
    }

}
