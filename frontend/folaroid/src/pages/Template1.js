import * as THREE from 'three';
import { React, Suspense, useEffect, useRef } from 'react';
import { Canvas } from '@react-three/fiber';
import { PerspectiveCamera } from '@react-three/drei';
import { useGLTF, useAnimations } from '@react-three/drei';
import { gsap } from 'gsap';
export const sizes = {
    width: window.innerWidth,
    height: window.innerHeight,
};
//Camera effect

export function Scene(props) {
    //const floor = useLoader(TextureLoader, 'images/grid.jpg');
    return (
        <mesh
            receiveShadow
            position-y={-1}
            rotation-x={-Math.PI / 2}
            scale={100}
        >
            <planeGeometry />
            <meshBasicMaterial color="lightgreen" /*map={floor}*/ />
        </mesh>
    );
}
export function House(props) {
    const { nodes, materials } = useGLTF('/models/house.gltf');
    return (
        <group {...props} dispose={null}>
            <mesh
                geometry={nodes.Cube.geometry}
                material={materials.Material}
                onClick={() => console.log('click')}
            ></mesh>
        </group>
    );
}
useGLTF.preload('/house.gltf');

export function Tree1(props) {
    const { nodes, materials } = useGLTF('/models/tree1.gltf');
    return (
        <group {...props} dispose={null}>
            <mesh
                geometry={nodes.tree009.geometry}
                material={materials.Lowpoly_naural}
                position={[-0.01, 0, 0]}
                scale={1.61}
            />
        </group>
    );
}

useGLTF.preload('/tree1.gltf');

let currentSection = 0;
let flag = 0;
function setSection(position, camera) {
    const newSection = Math.round(window.scrollY / window.innerHeight);

    if (currentSection !== newSection) {
        flag = 1;
        /*화면이동 */
        gsap.to(camera.position, {
            duration: 1,
            x: position[newSection][0],
            z: position[newSection][2] + 5,
        });
        currentSection = newSection;
        flag = 0;
    }
}
function setModal() {
    /*alert('프로젝트를 띄울거임');*/
}
const Template1 = () => {
    const position = [
        [-5, 0, 20],
        [7, 0, 10],
        [-10, 0, 0],
        [10, 0, -10],
        [-5, 0, -20],
    ];

    const camera = new THREE.PerspectiveCamera(
        75,
        sizes.width / sizes.height,
        0.1,
        1000
    );
    camera.position.set(-5, 2, 25);

    //스크롤
    window.addEventListener('scroll', function (event) {
        setSection(position, camera);
        setModal();
    });
    //클릭
    window.addEventListener('click', function (event) {
        if (flag === 0) {
            this.alert(currentSection);
        }
        //리스트인덱스에 맞는 모달을 만들거임
    });

    return (
        <>
            <Canvas
                camera={camera}
                style={{
                    position: 'fixed',
                    left: '0',
                    top: '0',
                    background: 'rgb(243, 245, 215)',
                }}
            >
                <Suspense fallback={null}>
                    {/* 부드럽게 마우스 이동 */}
                    <Scene />
                    {/* <OrbitControls
                        enableDamping={true}
                        maxDistance={40}
                        minDistance={2}
                        maxPolarAngle={Math.PI / 2}
                        minPolarAngle={Math.PI / 4}
                    /> */}
                    5
                    <PerspectiveCamera
                        far={1000}
                        near={0.1}
                        fov={75}
                        aspect={sizes.width / sizes.height}
                        position={[0, 0, 2]}
                    />
                    <House position={position[0]} />
                    <House position={position[1]} />
                    <House position={position[2]} />
                    <House position={position[3]} />
                    <House position={position[4]} />
                    <ambientLight intensity={0.5} />
                    <spotLight position={[10, 15, 10]} angle={0.3} />
                </Suspense>
            </Canvas>
            <div className="sections">
                <section className="section">
                    <h2>01</h2>
                </section>
                <section className="section">
                    <h2>02</h2>
                </section>
                <section className="section">
                    <h2>03</h2>
                </section>
                <section className="section">
                    <h2>04</h2>
                </section>
                <section className="section">
                    <h2>05</h2>
                </section>
            </div>
        </>
    );
};
export default Template1;
