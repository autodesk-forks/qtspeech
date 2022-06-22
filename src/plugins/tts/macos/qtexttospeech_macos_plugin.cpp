// Copyright (C) 2022 The Qt Company Ltd.
// SPDX-License-Identifier: LicenseRef-Qt-Commercial OR LGPL-3.0-only OR GPL-2.0-only

#include "qtexttospeech_macos_plugin.h"
#include "qtexttospeech_macos.h"

QTextToSpeechEngine *QTextToSpeechMacOSPlugin::createTextToSpeechEngine(const QVariantMap &parameters, QObject *parent, QString *errorString) const
{
    Q_UNUSED(errorString);
    return new QTextToSpeechEngineMacOS(parameters, parent);
}
