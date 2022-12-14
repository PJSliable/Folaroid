import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import * as api from '../lib/api/portfolioProjectAPI';

export const getProjectsThunk = createAsyncThunk(
    'portfolioProject/GET_PROJECTS',
    async (pfNo) => {
        const response = await api.getProjects(pfNo);
        return response.data;
    }
);

export const getProjectThunk = createAsyncThunk(
    'portfolioProject/GET_PROJECT',
    async (pjtNo) => {
        const response = await api.getProject(pjtNo);
        return response.data;
    }
);

export const deleteProjectThunk = createAsyncThunk(
    'portfolioProject/DELETE_PROJECT',
    async (pjtNo) => {
        const response = await api.deleteProject(pjtNo);
        return { data: response.data, pjtNo };
    }
);

export const createProjectThunk = createAsyncThunk(
    'portfolioProject/CREATE_PROJECT',
    async (payload) => {
        let pjt = {
            pfNo: payload.pfNo,
            pjtGithubUrl: payload.repo.html_url,
            pjtStar: payload.repo.stargazers_count,
            pjtTitle: payload.repo.name,
            pjtSubtitle: payload.repo.description,
            pjtImagesUrl: payload.repo.imagesUrl,
            pjtOneImageLocation:
                'https://images.velog.io/images/hosickk/post/0c6640b0-8bb7-4d10-95af-a5b4a58046ee/project-planning-header@2x.png',
            pjtId: payload.repo.id,
        };
        console.log(pjt);
        const response = await api.createProject(pjt);
        pjt.pjtNo = response.data;
        return pjt;
    }
);

export const saveProjectThunk = createAsyncThunk(
    'portfolioProject/SAVE_PROJECT',
    async (pjt) => {
        const response = await api.saveProject(pjt);
        console.log(pjt);
        return response.data;
    }
);

export const saveImagesThunk = createAsyncThunk(
    'portfolioProject/SAVE_IMAGES',
    async (data) => {
        const response = await api.saveImages(data);
        return response.data;
    }
);

const initProject = {
    pjtNo: undefined,
    pfNo: undefined,
    pjtTitle: '',
    pjtSubtitle: '',
    pjtUrl: '',
    pjtGithubUrl: '',
    pjtStar: 0,
    pjtOneImageLocation: '',
    pjtJson: '',
    pjtId: '',
};

export const portfolioProject = createSlice({
    name: 'portfolioProject',
    initialState: {
        projects: [],
        isProject: false,
        isProjects: false,
        project: initProject,
        isSave: false,
        isJson: false,
    },
    reducers: {
        changeInput: (state, action) => {
            const { name, value } = action.payload;
            state.project[name] = value;
        },
        changeProjectImage: (state, action) => {
            state.project.pjtOneImageLocation = action.payload;
        },
        clearProject: (state, action) => {
            state.project = initProject;
            state.isProject = false;
            state.isProjects = false;
            state.isSave = false;
            state.isJson = false;
        },
        clearProjects: (state, action) => {
            state.isProjects = false;
        },
        setProjectJson: (state, action) => {
            state.project.pjtJson = JSON.stringify(action.payload);
            state.isJson = true;
        },
    },
    extraReducers: {
        [getProjectsThunk.pending.type]: (state) => {
            state.isProjects = false;
        },
        [getProjectsThunk.fulfilled.type]: (state, action) => {
            state.projects = action.payload;
            state.isProjects = true;
        },
        [getProjectThunk.fulfilled.type]: (state, action) => {
            state.project = action.payload;
            state.isProject = true;
        },
        [deleteProjectThunk.fulfilled.type]: (state, { payload }) => {
            state.projects = state.projects.filter(
                (pjt) => pjt.pjtNo !== payload.pjtNo
            );
        },
        [createProjectThunk.pending.type]: (state) => {
            state.isProject = false;
        },
        [createProjectThunk.fulfilled.type]: (state, { payload }) => {
            state.project = payload;
            state.isProject = true;
        },
        [saveProjectThunk.pending.type]: (state) => {
            state.isSave = false;
        },
        [saveProjectThunk.fulfilled.type]: (state) => {
            state.isSave = true;
        },
    },
});

export default portfolioProject.reducer;
