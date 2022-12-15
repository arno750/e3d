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

The three most commonly used transformations (translation, scaling and rotation) are treated in the same way with 4x4 matrices.

Translation defined by $[T_x T_y T_z]$:

```math
T =\begin{bmatrix}
1 & 0 & 0 & 0\\
0 & 1 & 0 & 0\\
0 & 0 & 1 & 0\\
T_x & T_y & T_z & 1
\end{bmatrix}
```

Scaling defined by $[S_x S_y S_z]$:

```math
S =\begin{bmatrix}
S_x & 0 & 0 & 0\\
0 & S_y & 0 & 0\\
0 & 0 & S_z & 0\\
0 & 0 & 0 & 1
\end{bmatrix}
```

Rotation (counterclockwise) about the _X_, _Y_ and _Z_ axis respectively defined by the $\theta$ angle:

```math
R_x =\begin{bmatrix}
1 & 0 & 0 & 0\\
0 & \cos \theta & \sin \theta & 0\\
0 & -\sin \theta & \cos \theta & 0\\
0 & 0 & 0 & 1
\end{bmatrix}
```

```math
R_y =\begin{bmatrix}
\cos \theta & 0 & -\sin \theta & 0\\
0 & 1 & 0 & 0\\
\sin \theta & 0 & \cos \theta & 0\\
0 & 0 & 0 & 1
\end{bmatrix}
```

```math
R_z =\begin{bmatrix}
\cos \theta & \sin \theta & 0 & 0\\
-\sin \theta & \cos \theta & 0 & 0\\
0 & 0 & 1 & 0\\
0 & 0 & 0 & 1
\end{bmatrix}
```
