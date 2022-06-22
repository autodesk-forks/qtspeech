// Copyright (C) 2022 The Qt Company Ltd.
// SPDX-License-Identifier: LicenseRef-Qt-Commercial OR LGPL-3.0-only OR GPL-2.0-only

#include "qtexttospeech_mock_plugin.h"
#include "qtexttospeech_mock.h"

QTextToSpeechEngine *QTextToSpeechMockPlugin::createTextToSpeechEngine(const QVariantMap &parameters, QObject *parent, QString *errorString) const
{
    Q_UNUSED(errorString);
    return new QTextToSpeechEngineMock(parameters, parent);
}
