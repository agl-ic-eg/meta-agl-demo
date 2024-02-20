---
description: Feature agl-kvm-host-audio
authors: Scott Murray <scott.murray@konsulko.com>
---

### Feature agl-kvm-host-audio

* Enables support for running audio services on host in KVM+QEMU demo images

### Dependent features pulled by agl-kvm-host-audio

The following features are pulled:

* agl-kvm agl-kvm-host-kuksa

Note that enabling this feature results in a configuration where building images
other than agl-kvm-demo-platform will likely not give the desired results.
