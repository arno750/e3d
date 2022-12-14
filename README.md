# e3d

[![GitLab CI](https://gitlab.com/arno750/e3d/badges/main/pipeline.svg)](https://gitlab.com/arno750/e3d/-/commits/main)

3D engine (including renderer and viewer)

## Support

[open an issue here](https://gitlab.com/arno750/e3d/-/issues).

## Authors & contributors

Original setup of this repository by [Arnaud WIELAND](https://gitlab.com/arno750).

## License

Every details about licence are in a [separate document](LICENSE).

## About the project

This implementation is freely based on the book: Watt A. (1993) _3D Computer Graphics_ (second edition). Addison-Wesley.

It was realized in december 2011 for self educational purpose.

In december 2022 the project has been reviewed, fixed and documented.

## Rendering examples

### The Utah teapot

![The Utah teapot](images/UtahTeapot.png)

### A sphere and a torus

![Sphere ane torus](images/SphereAndTorus.png)

### A Bezier patch

![Bezier patch](images/BezierPatch.png)

![Bezier patch](images/BezierPatchMesh.png)

## Affine transformations

The points are defined using homogeneous coordinates $[x y z w]$ with $w$ always taken to be 1.

```math
\begin{bmatrix}
   a & b \\
   c & d
\end{bmatrix}
```

The three most commonly used transformations (translation, scaling and rotation) are treated in the same way with 4x4 matrices. $(ax^2 + bx + c = 0)$

$$ x = {-b \pm \sqrt{b^2-4ac} \over 2a} $$
