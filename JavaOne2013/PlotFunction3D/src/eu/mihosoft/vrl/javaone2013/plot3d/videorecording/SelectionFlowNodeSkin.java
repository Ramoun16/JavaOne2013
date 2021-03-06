/*
 * Copyright 2012 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.videorecording;

import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
import javafx.scene.Node;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import java.text.DecimalFormat;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Custom flownode skin. In addition to the basic node visualization from
 * VWorkflows this skin adds custom visualization of value objects.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class SelectionFlowNodeSkin extends CustomFlowNodeSkinNew {

    public SelectionFlowNodeSkin(FXSkinFactory skinFactory,
            VNode model, VFlow controller) {
        super(skinFactory, model, controller);
    }

    @Override
    protected Node createView() {

//        // see jfxtras ensemble project for more sample code (gauges by @hansolo_)
//        // https://github.com/JFXtras/jfxtras-ensemble/tree/master/src/ensemble/samples
//        StyleModel style =
//                StyleModelBuilder.create()
//                .lcdDesign(LcdDesign.GREEN_BLACK)
//                .lcdValueFont(Gauge.LcdFont.LCD)
//                .lcdUnitStringVisible(true)
//                .lcdThresholdVisible(true)
//                .build();
//
//        Lcd lcd1 = LcdBuilder.create()
//                .styleModel(style)
//                .threshold(40)
//                .bargraphVisible(true)
//                .minMeasuredValueVisible(true)
//                .minMeasuredValueDecimals(3)
//                .maxMeasuredValueVisible(true)
//                .maxMeasuredValueDecimals(3)
//                .formerValueVisible(true)
//                .title("VWorkflows")
//                .unit("°C")
//                .value((Integer) getModel().getValueObject().getValue())
//                .build();
//
//        lcd1.setPrefSize(250, 70);
//
//        return lcd1;

        final Lcd lcd1 = LcdBuilder.create()
                .styleClass(Lcd.STYLE_CLASS_GREEN_BLACK)
                .minMeasuredValueVisible(false)
                .minMeasuredValueDecimals(3)
                .maxMeasuredValueVisible(false)
                .maxMeasuredValueDecimals(3)
                .minValue(-100)
                .maxValue(100)
                .formerValueVisible(true)
                .title("Value at [-,-]")
                .unit("")
                .animationDurationInMs(300)
                .alarmVisible(false)
                .animated(true)
                .batteryVisible(false)
                .decimals(2)
                .build();


        lcd1.setPrefSize(250, 70);

        lcd1.setCache(true);

        final DecimalFormat df = new DecimalFormat("0.00");

        if (getModel().getValueObject().getValue() instanceof SelectionResult) {
            SelectionResult selResult = (SelectionResult) getModel().getValueObject().getValue();

            if (selResult.getValue() != null) {
                lcd1.setValue(selResult.getValue());
                lcd1.setTitle("Value at ["
                        + df.format(selResult.getSelectionPoint().getX())
                        + ", " + df.format(selResult.getSelectionPoint().getY()) + "]");
            }
        }

        getModel().getValueObject().valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (newValue instanceof SelectionResult) {
                    SelectionResult selResult = (SelectionResult) newValue;
                    if (selResult.getValue() != null) {
                        lcd1.setValue(selResult.getValue());

                        lcd1.setTitle("Value at ["
                                + df.format(selResult.getSelectionPoint().getX())
                                + ", " + df.format(selResult.getSelectionPoint().getY()) + "]");
                    }
                }
            }
        });

        return lcd1;


    }
}
