#!/bin/bash
set -x

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
# we do not import 
DISTRO_FEATURES:append = " systemd smack"
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
#AGL_FEATURES += "aglcore agldemo"
#DISTRO_FEATURES:append = " appfw"

# workaround:
#ERROR: Nothing RPROVIDES 'qtwebengine-qmlplugins' (but /home/dl9pf/AGL/master-newlayout/external/meta-qt5/recipes-qt/qt5/qt5-creator_git.bb RDEPENDS on or otherwise requires it)
BBMASK += "meta-qt5/recipes-qt/qt5/qt5-creator_git.bb"


EOF


yocto-check-layer -d \
	--dependency \
	$AGLROOT/external/meta-openembedded/meta-oe \
	$AGLROOT/external/meta-openembedded/meta-oe \
	$AGLROOT/external/meta-openembedded/meta-python \
	$AGLROOT/external/meta-openembedded/meta-networking \
	$AGLROOT/external/meta-security \
	$AGLROOT/external/meta-openembedded/meta-perl \
	$AGLROOT/meta-agl/meta-agl-core \
	$AGLROOT/external/meta-qt5 \
	--additional-layers \
	$AGLROOT/external/meta-qt5 \
	$AGLROOT/external/meta-python2 \
	-- \
	$AGLROOT/meta-agl-demo

cat ${TMPROOT}/testbuild-ycl/conf/bblayers.conf

[ $? = 0 ] && rm -rf ${TMPROOT}/testbuild-ycl

exit 0
