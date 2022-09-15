package nithra.namma_tiruchengode.autoimageslider.IndicatorView.animation;

import androidx.annotation.NonNull;

import nithra.namma_tiruchengode.autoimageslider.IndicatorView.animation.controller.AnimationController;
import nithra.namma_tiruchengode.autoimageslider.IndicatorView.animation.controller.ValueController;
import nithra.namma_tiruchengode.autoimageslider.IndicatorView.draw.data.Indicator;


public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
