package qssq666.cn.kotlin.bannerdemo.transformer;

import android.view.View;

/**
 * Created by 情随事迁(qssq666@foxmail.com) on 2017/5/3.
 */

public class GallyPage1Transformer extends ABaseTransformer {

    private static final float min_scale = 0.85f;

    @Override
    protected void onTransform(View page, float position) {
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
        float rotate = 20 * Math.abs(position);
        if (position < -1) {

        } else if (position < 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
    }
}
