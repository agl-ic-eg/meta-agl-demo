# Enable EGL and KMS platform plugins to allow demos of Qt compositor
# without Wayland
PACKAGECONFIG_GL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', ' eglfs kms gbm', '', d)}"

PACKAGECONFIG:append = " icu fontconfig sql-sqlite"
