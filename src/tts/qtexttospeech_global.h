/****************************************************************************
**
** Copyright (C) 2021 The Qt Company Ltd.
** Contact: https://www.qt.io/licensing/
**
** This file is part of the Qt Speech module of the Qt Toolkit.
**
** $QT_BEGIN_LICENSE:COMM$
**
** Commercial License Usage
** Licensees holding valid commercial Qt licenses may use this file in
** accordance with the commercial license agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and The Qt Company. For licensing terms
** and conditions see https://www.qt.io/terms-conditions. For further
** information use the contact form at https://www.qt.io/contact-us.
**
** $QT_END_LICENSE$
**
**
**
**
**
**
**
**
**
**
**
**
**
**
**
**
****************************************************************************/



#ifndef QTEXTTOSPEECH_GLOBAL_H
#define QTEXTTOSPEECH_GLOBAL_H

#include <QtCore/qglobal.h>

QT_BEGIN_NAMESPACE

#ifndef QT_STATIC
# if defined(QTEXTTOSPEECH_LIBRARY)
#   define Q_TEXTTOSPEECH_EXPORT Q_DECL_EXPORT
# else
#   define Q_TEXTTOSPEECH_EXPORT Q_DECL_IMPORT
# endif
#else
# define Q_TEXTTOSPEECH_EXPORT
#endif

QT_END_NAMESPACE

#endif
