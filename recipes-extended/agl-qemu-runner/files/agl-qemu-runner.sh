#!/bin/bash
# SPDX-License-Identifier: Apache-2.0

if [ -z "$1" ]; then
    echo "Usage: ${basename $0} <image name>"
    exit 1
fi
image="$1"

conf="/etc/agl-qemu-runner/${image}.conf"
if [ ! -f "$conf" ]; then
    echo "No configuration file $conf"
    exit 1
fi

. $conf

arch="$(uname -m)"
if [ -z "$QEMU_IMAGE_ARCH" ]; then
    QEMU_IMAGE_ARCH="virtio-${arch}"
fi

disk="/var/lib/machines/${image}/${image}-${QEMU_IMAGE_ARCH}.ext4"
if [ ! -f "$disk" ]; then
    echo "No disk image for $image"
    exit 1
fi
kernel="/var/lib/machines/${image}/Image-${QEMU_IMAGE_ARCH}.bin"
if [ ! -f "$kernel" ]; then
    echo "No kernel for $image"
    exit 1
fi

TASKSET_CMD=""
if [ -n "$QEMU_TASKSET_CPUS" ]; then
    TASKSET_CMD="taskset -c ${QEMU_TASKSET_CPUS}"
fi
    
export SDL_VIDEODRIVER=wayland 
export XDG_RUNTIME_DIR=/run/user/1001
# The following may be needed if the socket is not wayland-0, as SDL
# seems to lack detection logic for that case.
#export WAYLAND_DISPLAY=wayland-1

# This sets the XDG app id, which we need for setting outputs with
# agl-compositor
export SDL_VIDEO_WAYLAND_WMCLASS="${image}"

${TASKSET_CMD} \
qemu-system-${arch} \
	-enable-kvm \
	-machine virt,gic-version=max,iommu=smmuv3 \
	-cpu host \
	${QEMU_SMP_OPT} \
	${QEMU_MEM_OPT} \
	-kernel $kernel \
	-append "${QEMU_KERNEL_CMDLINE_APPEND}" \
	-drive id=disk0,file=${disk},format=raw,if=none \
	-serial mon:pty \
	-object rng-random,filename=/dev/urandom,id=rng0 \
	-device virtio-blk-device,drive=disk0 \
	-device virtio-rng-device,rng=rng0 \
	${QEMU_NET_OPT} \
	${QEMU_INPUT_OPT} \
	-global virtio-mmio.force-legacy=false \
	-device virtio-gpu-gl-device \
	-display sdl,gl=on -vga std \
	${QEMU_AUDIO_OPT} \
	${QEMU_CAN_OPT} \
	${QEMU_EXTRA_OPT} \
	-full-screen
