/****************************************************************************
**
** Copyright (C) 2014 Digia Plc and/or its subsidiary(-ies).
** Contact: http://www.qt-project.org/legal
**
** This file is part of the Android port of the Qt Toolkit.
**
** $QT_BEGIN_LICENSE:LGPL$
** Commercial License Usage
** Licensees holding valid commercial Qt licenses may use this file in
** accordance with the commercial license agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Digia.  For licensing terms and
** conditions see http://qt.digia.com/licensing.  For further information
** use the contact form at http://qt.digia.com/contact-us.
**
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Digia gives you certain additional
** rights.  These rights are described in the Digia Qt LGPL Exception
** version 1.1, included in the file LGPL_EXCEPTION.txt in this package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
**
** $QT_END_LICENSE$
**
****************************************************************************/

package org.qtproject.qt5.android.speech;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.os.Build;
import android.util.Log;
import java.lang.Float;
import java.util.HashMap;

public class QtTextToSpeech
{
    // Native callback functions
    native public void notifyError(long id);
    native public void notifyReady(long id);
    native public void notifySpeaking(long id);

    private TextToSpeech mTts;
    private final long mId;
    private float mPitch = 1.0f;

    // OnInitListener
    private final OnInitListener mTtsChangeListener = new OnInitListener() {
        @Override
        public void onInit(int status) {
            Log.w("QtTextToSpeech", "tts initialized");
            if (status == TextToSpeech.SUCCESS) {
                notifyReady(mId);
            } else {
                notifyError(mId);
            }
        }
    };

    // UtteranceProgressListener
    private final UtteranceProgressListener mTtsUtteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onDone(String utteranceId) {
            Log.w("UtteranceProgressListener", "onDone");
            if (utteranceId.equals("UtteranceId")) {
                notifyReady(mId);
            }
        }

        @Override
        public void onError(String utteranceId) {
            Log.w("UtteranceProgressListener", "onError");
            if (utteranceId.equals("UtteranceId")) {
                notifyReady(mId);
            }
        }

        @Override
        public void onError(String utteranceId, int errorCode) {
            Log.w("UtteranceProgressListener", "onError");
            if (utteranceId.equals("UtteranceId")) {
                notifyReady(mId);
            }
        }

        @Override
        public void onStart(String utteranceId) {
            Log.w("UtteranceProgressListener", "onStart");
            if (utteranceId.equals("UtteranceId")) {
                notifySpeaking(mId);
            }
         }
     };

    public static QtTextToSpeech open(final Context context, final long id)
    {
        return new QtTextToSpeech(context, id);
    }

    QtTextToSpeech(final Context context, final long id) {
        mId = id;
        mTts = new TextToSpeech(context, mTtsChangeListener);
        mTts.setOnUtteranceProgressListener(mTtsUtteranceProgressListener);

        // Read pitch from settings
        ContentResolver resolver = context.getContentResolver();
        try {
            float pitch = Settings.Secure.getFloat(resolver, android.provider.Settings.Secure.TTS_DEFAULT_PITCH);
            mPitch = pitch / 100.0f;
        } catch (SettingNotFoundException e) {
            mPitch = 1.0f;
        }
    }

    public void say(String text)
    {
        Log.w("QtTextToSpeech", text);

        int result = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            result = mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UtteranceId");
        } else {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UtteranceId");
            result = mTts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        }

        Log.w("QtTextToSpeech", "RESULT: " + Integer.toString(result));
    }

    public void stop()
    {
        Log.w("QtTextToSpeech", "STOP");
        mTts.stop();
    }

    public float pitch()
    {
        return mPitch;
    }

    public int setPitch(float pitch)
    {
        if (Float.compare(pitch, mPitch) == 0)
            return TextToSpeech.ERROR;

        int success = mTts.setPitch(pitch);
        if (success == TextToSpeech.SUCCESS)
            mPitch = pitch;

        return success;
    }

    public void shutdown()
    {
        mTts.shutdown();
    }
}
