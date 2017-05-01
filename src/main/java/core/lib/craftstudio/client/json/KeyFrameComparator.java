package com.leviathanstudio.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;

import com.google.gson.JsonElement;
import com.squareup.javapoet.MethodSpec;

public class KeyFrameComparator
{

    int keyFrameCount;

    public void preInit(CSAnimationReader animationReader, MethodSpec.Builder initializeAllFrames) {
        List<Integer> list = new LinkedList<>();

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetRotation : animationReader.getRotation(entrySet).getAsJsonObject().entrySet()) {

                this.keyFrameCount = Integer.parseInt(entrySetRotation.getKey()) / 2;
                list.add(this.keyFrameCount);

            }

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetPosition : animationReader.getPosition(entrySet).getAsJsonObject().entrySet()) {

                this.keyFrameCount = Integer.parseInt(entrySetPosition.getKey()) / 2;
                list.add(this.keyFrameCount);

            }

        List<Integer> unDuplicate = new ArrayList<>();
        List<Integer> duplicate = new ArrayList<>(findDuplicates(list));

        for (Integer i : list)
            if (!duplicate.contains(i))
                unDuplicate.add(i);
        unDuplicate.addAll(duplicate);

        for (int i : unDuplicate)
            initializeAllFrames.addStatement("$T keyframe$L = new $T()", ClassUtils.KEY_FRAME, i, ClassUtils.KEY_FRAME);

    }

    public void postInit(CSAnimationReader animationReader, MethodSpec.Builder initializeAllFrames) {
        List<Integer> list = new LinkedList<>();

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetRotation : animationReader.getRotation(entrySet).getAsJsonObject().entrySet()) {

                this.keyFrameCount = Integer.parseInt(entrySetRotation.getKey()) / 2;
                list.add(this.keyFrameCount);

            }

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetPosition : animationReader.getPosition(entrySet).getAsJsonObject().entrySet()) {

                this.keyFrameCount = Integer.parseInt(entrySetPosition.getKey()) / 2;
                list.add(this.keyFrameCount);

            }

        List<Integer> unDuplicate = new ArrayList<>();
        List<Integer> duplicate = new ArrayList<>(findDuplicates(list));

        for (Integer i : list)
            if (!duplicate.contains(i))
                unDuplicate.add(i);
        unDuplicate.addAll(duplicate);

        for (int i : unDuplicate)
            initializeAllFrames.addStatement("this.keyFrames.put($L, keyframe$L)", i, i);

    }

    private static Set<Integer> findDuplicates(List<Integer> listContainingDuplicates) {

        final Set<Integer> setToReturn = new HashSet<>();
        final Set<Integer> set1 = new HashSet<>();

        for (Integer yourInt : listContainingDuplicates)
            if (!set1.add(yourInt))
                setToReturn.add(yourInt);
        return setToReturn;
    }
}
