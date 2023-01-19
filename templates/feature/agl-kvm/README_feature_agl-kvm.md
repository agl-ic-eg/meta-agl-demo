---
description: Feature agl-kvm
authors: Scott Murray <scott.murray@konsulko.com>
---

### Feature agl-kvm

* Enables support for building multiconfig based KVM+QEMU demo images

### Dependent features pulled by agl-kvm

The following features are pulled:

* agl-demo

Note that enabling this feature results in a configuration where building images
other than agl-kvm-demo-platform will likely not give the desired results.
