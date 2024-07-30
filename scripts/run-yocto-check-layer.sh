#!/bin/bash
#set -x

SCRIPTPATH="$( cd $(dirname $0) >/dev/null 2>&1 ; pwd -P )"
echo $SCRIPTPATH
AGLROOT="$SCRIPTPATH/../.."
POKYDIR="$AGLROOT/external/poky"
TMPROOT=`mktemp -d`

rm -rf ${TMPROOT}/testbuild-ycl || true
mkdir -p ${TMPROOT}/testbuild-ycl
cd ${TMPROOT}/testbuild-ycl

source $POKYDIR/oe-init-build-env .

cat << EOF >> conf/local.conf
# just define defaults
AGL_FEATURES ?= ""
AGL_EXTRA_IMAGE_FSTYPES ?= ""

# important settings imported from poky-agl.conf
# we cannot import the distro config right away
# as the initial values are poky only till the layer
# is added in

AGL_DEFAULT_DISTRO_FEATURES := "usrmerge largefile opengl wayland pam bluetooth bluez5 3g polkit"
DISTRO_FEATURES:append := " systemd wayland pam \${AGL_DEFAULT_DISTRO_FEATURES}"
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"

# required by meta-flutter/recipes-graphics/toyota/flutter-auto_git.bb"
LICENSE_FLAGS_ACCEPTED += "commercial"

# Need to ignore as we have more recent version
# meta-openembedded/meta-oe/recipes-graphics/libvncserver/libvncserver_0.9.13.bb
BBMASK += "meta-openembedded/meta-oe/recipes-graphics/libvncserver/libvncserver_*"

# due to this, we also ignore:
BBMASK += "meta-oe/recipes-support/remmina/remmina_* meta-oe/recipes-graphics/x11vnc/x11vnc_* "


EOF

yocto-check-layer --no-auto-dependency \
	--dependency \
	$AGLROOT/meta-agl/meta-agl-core \
	$AGLROOT/meta-agl/meta-app-framework \
	$AGLROOT/external/meta-openembedded/meta-oe \
	$AGLROOT/external/meta-openembedded/meta-python \
	$AGLROOT/external/meta-openembedded/meta-networking \
	$AGLROOT/external/meta-openembedded/meta-multimedia \
	$AGLROOT/external/meta-flutter \
	$AGLROOT/external/meta-flutter/meta-flutter-apps \
	$AGLROOT/external/meta-qt6 \
	$AGLROOT/external/meta-clang \
	-- \
	$AGLROOT/meta-agl-demo


[ $? = 0 ] && rm -rf ${TMPROOT}/testbuild-ycl

exit 0
