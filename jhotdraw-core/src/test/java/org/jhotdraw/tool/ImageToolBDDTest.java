package org.jhotdraw.tool;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class ImageToolBDDTest extends ScenarioTest<GivenImageToolSetup, WhenImageToolAction, ThenImageToolOutcome> {

    @Test
    public void user_can_insert_an_image_on_the_canvas() {
        given().a_drawing_editor()
                .and().a_drawing_view()
                .and().an_image_tool_with_prototype();

        when().the_user_selects_an_image("path/to/image.png")
                .and().the_user_places_the_image_on_canvas(50, 50, 200, 150);

        then().the_canvas_contains_an_image_at(50, 50)
                .and().the_image_has_dimensions(150, 100);
    }
}
