# e3d

[![GitLab CI](https://gitlab.com/arno750/e3d/badges/main/pipeline.svg)](https://gitlab.com/arno750/e3d/-/commits/main)

3D engine (including renderer and viewer)

## Support

[open an issue here](https://gitlab.com/arno750/e3d/-/issues).

## Authors & contributors

Original setup of this repository by [Arnaud WIELAND](https://gitlab.com/arno750).

## License

Every details about licence are in a [separate document](LICENSE).

## Basics

This implementation is freely based on the book: Watt A. (1993) _3D Computer Graphics_ (second edition). Addison-Wesley.

It is implicitly using homogeneous coordinates `[x y z w]` with _w_ always taken to be 1. The three most commonly used transformations (translation, scaling and rotation) can be treated in the same way with 4x4 matrices.

When $a \ne 0$, there are two solutions to $(ax^2 + bx + c = 0)$ and they are
$$ x = {-b \pm \sqrt{b^2-4ac} \over 2a} $$