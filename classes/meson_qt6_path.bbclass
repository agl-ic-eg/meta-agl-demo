inherit meson pkgconfig qt6-paths

# fix meson cannot find Qt6 uic
do_configure:prepend () {
   QT_HOST_LIBEXECS_DIR="$(qmake -query QT_HOST_LIBEXECS)"
   bbwarn "QT_HOST_LIBEXECS_DIR ${QT_HOST_LIBEXECS_DIR}"
   export PATH=$PATH:${QT_HOST_LIBEXECS_DIR}
}

