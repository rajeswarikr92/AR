package com.example.ar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class MainActivity extends AppCompatActivity {
    private ArFragment arCoreFragment;

    private ModelLoader modelLoader;
    private ModelRenderable andyRenderable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arCoreFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(

//If an error occurs...//

                        throwable -> {

//...then print the following message to Logcat//

                            Log.e("error", "Unable to load renderable");
                            return null;
                        });

        arCoreFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        return;
                    }

                    Anchor anchor = hitResult.createAnchor();

//Build a node of type AnchorNode//

                    AnchorNode anchorNode = new AnchorNode(anchor);

//Connect the AnchorNode to the Scene//

                    anchorNode.setParent(arCoreFragment.getArSceneView().getScene());

//Build a node of type TransformableNode//

                    TransformableNode transformableNode = new TransformableNode(arCoreFragment.getTransformationSystem());

//Connect the TransformableNode to the AnchorNode//

                    transformableNode.setParent(anchorNode);

//Attach the Renderable//

                    transformableNode.setRenderable(andyRenderable);

//Set the node//

                    transformableNode.select();
                });





    }

    public void addNodeToScene(Anchor anchor, ModelRenderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arCoreFragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arCoreFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }
    public void onException(Throwable throwable){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(throwable.getMessage())
                .setTitle("AR error!");
        AlertDialog dialog = builder.create();
        dialog.show();
        return;
    }

    private void addObject(Uri arModel) {

    }
}
